#include "Player.hh"


/**
 * Write the name of your player and save this file
 * with the same name and .cc extension.
 */
#define PLAYER_NAME doom_boy


struct PLAYER_NAME : public Player {

  /**
   * Factory: returns a new instance of this class.
   * Do not modify this function.
   */
  static Player* factory () {
    return new PLAYER_NAME;
  }

  /**
   * Types and attributes for your player can be defined here.
   */
  typedef vector<int> VI;

  struct DD {
    int dist = 1000;
    int dir;
  };

  int dir_original;

  VI aux = {0,1,2,3,4,5,6,7};
  VI perm;

  int sun_start, sun_end;

  void check_dist(int dist, vector<DD>& dd, int i) {
    if (dist < dd[i].dist) {
      dd[i].dist = dist;
      dd[i].dir = dir_original;
    }
  }

  bool good_to_go(Pos& actual) { //Nos dice si es seguro salir a la superfície
    int y = actual.j;
    if (round()%40 > 20) { //Si el sol no está partido por el mapa.
      if (y > sun_start or y < sun_end){
        if (y > sun_start) return (y - sun_start) > 10;
        if (y <= sun_end) return true;
      }
    }
    else if (y <= sun_end and y > sun_start) return(y - sun_start) > 10;
    return false;
  }

  int verification(vector<DD>& dd, Pos& actual) {
    if (actual.k == 0) {
      if (dd[2].dist == 0 and good_to_go(actual) and dd[4].dist < 10) return 8;
      if (dd[0].dist < 1000 and dd[1].dist < 1000) {
        if (dd[0].dist < 5) return choose_dir(dd[0].dir, actual, false); //Si tenemos PERRO cerca nos vamos alejamos de él.
        else if (dd[2].dist < 2 and good_to_go(actual) and dd[4].dist < 8) return dd[2].dir; 
        else if (dd[3].dist < 3) return choose_dir(dd[3].dir, actual, false); //Si somos Pioneer y tenemos Furyan rival al lado, nos alejamos de él.
        return dd[1].dir; //Sino, vamos hacia hueco libre o rival mas cercano.
      }
    }
    else {
        Pos aux;
        if (dd[4].dist < 1000 and dd[5].dist < 1000) { //Si ya encontramos una gema y un necromonger.
          aux = actual + Dir(dd[4].dir);
          if (dd[4].dist < 10 and good_to_go(aux)) return dd[4].dir; //Si no hay enemigos y la gema esta en buen sitio, vamos hacia la gema mas cercana.
          else if (dd[2].dist == 0) return 9;
          if (dd[5].dist < 3) { //Si tenemos necromonger al lado.
              if (dd[2].dist == 0) {
                aux = actual + Dir(9);
                if (cell(aux).id == -1) return 9; //Si tenemos Elevador nos piramos.
              }
              if (dd[2].dist < 4 and dd[2].dist != 0) return dd[2].dir; //Si tenemos un ascensor al lado nos vamos hacia él.
              cerr << "entramos" << endl;
              return choose_dir (dd[5].dir, actual, true);//Sino nos alejamos dnd sea, siempre que cumpla los parámetros.
          }

          aux = actual + Dir(dd[2].dir);
          if (dd[2].dist < 6 and good_to_go(aux)) return dd[2].dir;
          int y = actual.j;
          if (round()%40 > 20) { //Si el sol no está partido por el mapa.
            if (y > sun_start or y < sun_end){
              if ((y > sun_start and (y - sun_start) > 0) or y < sun_end) return random(0, 2);
            }
          }
          else if ((y <= sun_end and y > sun_start) and (y - sun_start) > 0) return random(0, 2);
        }
    }
    return -1;   
  }

  int p_direction(Pos& p, bool mode) {
    //Guardamos en el stack la posición y la dirección.
    queue<pair<Pos, int>> Q; //Así sabremos la dirección a partir de la cual partimos para acabar encontrando lo que sea.
    vector<vector<VI>> dist(40, vector<VI>(80, VI(2, -1)));
    bool original = true;
    int distancia;

    Q.push({p, 0});
    dist[p.i][p.j][p.k] = 0;

    vector<DD> dd(6); //dd[0] -> hellhound, dd[1] -> unidad/cell, dd[2] -> elevator, dd[3] -> enemies, dd[4] -> gems, dd[5] -> Necromongers.
    int ret = -1;

    while (Q.size() > 0) {
      Pos actual = Q.front().first;
      dir_original = Q.front().second;
      int x = actual.i;
      int y = actual.j;
      int z = actual.k;
      Q.pop();
      distancia = dist[x][y][z];

      if (cell(actual).type == Elevator and not mode) {
        check_dist(distancia, dd, 2);
      }

      int id = cell(actual).id;

      if (z == 0) {
        Pos up = actual + Dir(8); //Nos da la posición de arriba.
        int id2 = cell(up).id;

        if (id != -1 and unit(id).player != me()) { //Miramos si la celda esta ocupada por alguien del equipo rival
          if (unit(id).type == Hellhound) { //Si es un hellhound, guardamos la distáncia a la que estámos y la dirección.
            check_dist(distancia, dd, 0);
          }
          else {
            if (mode) { //MODO FURYAN
              check_dist(distancia, dd, 1);
            }
            else { //MODO PIONEER
              if (unit(id).type == Furyan) { //Si es furyan enemigo, guardamos la distancia a la que esta y en que dirección.
                check_dist(distancia, dd, 3);
              }
            }
          }
        }
        else { //Si no hay ninguna unidad presente, nos ponemos modo PIONEER.
          if (not mode) {
            if (cell(actual).type == Cave and cell(actual).owner != me()) { //Si es una celda vacia, nos guardamos la distancia a la que esta y la dirección.
              check_dist(distancia, dd, 1);
            }
            if (cell(up).gem) { //Si estamos en el subsuelo y encima nuestro hay una gema, nos lo guardamos.
              check_dist(dist[x][y][1], dd, 4);
            }
            if (id2 != -1 and unit(id2).type == Necromonger) {
              check_dist(distancia, dd, 5);
            }
          }
        }
      }
      else { //Estamos en la superfície
        if (id != -1 and unit(id).type == Necromonger) check_dist(dist[x][y][z], dd, 5); //Enemigo -> Necromonger
        else if (cell(actual).gem) check_dist(dist[x][y][z], dd, 4); //GEMA
      }
      ret = verification(dd, p); //Miramos si hemos encontrado ya un hellhound y un enemigo/celda.
      if (ret != -1) return ret;

      perm = random_permutation(8);
      int k;
      for (int i = 0; i < 8; ++i) {
        k = aux[perm[i]];
        Pos m = actual;
        m += Dir(k);
        execute_proc (m, dist, Q, original, k, dir_original, dist[x][y][z]);
      }
      original = false;
    }
    return 1;
  }

  void create_pq(int& dir, vector<int>& pq, bool sup) {
    int k;
    vector<int> pos = {4, 3, 3, 2, 2, 1, 1, 0};
    for (k = 0; k < 8; ++k) {
      int pri;
      if (k%2 == 0) pri = ((dir+8) + pos[k])%8;
      else pri = ((dir+8) - pos[k])%8;
      if (sup) {
        if (pri >= 0 and pri <= 4) {
          cerr << pri << endl;
          pq.push_back(pri);
        }
      }
      else pq.push_back(pri);
    } 
  }

  void execute_proc (Pos& m, vector<vector<VI>>& dist, queue<pair<Pos, int>>& Q, bool& original, int& k, int& dir_original, int distancia) {
    if (pos_ok(m) and dist[m.i][m.j][m.k] == -1) {
      Cell c = cell(m); 
      if (c.type != Rock) {
        if (original) Q.push({m,k});
        else Q.push({m, dir_original});
        dist[m.i][m.j][m.k] = distancia + 1;
      } 
    }
  }

  int choose_dir (int& dir, Pos& p, bool sup) {
    queue<pair<Pos, int>> Q; //Así sabremos la dirección a partir de la cual partimos para acabar encontrando lo que sea.
    vector<VI> dist(40, VI(80, -1));
    bool original = true;

    Q.push({p, 0});
    dist[p.i][p.j] = 0;

    int distancia;

    int maxDist = -1;
    int dir_final, k;
    dir_final = dir;
    vector<int> pq;
    create_pq(dir, pq,sup);

    while (Q.size() > 0 and maxDist < 15) { //Un cambio que no esta en "mistermo"
      Pos actual = Q.front().first;
      dir_original = Q.front().second;
      int x = actual.i;
      int y = actual.j;
      Q.pop();

      distancia = dist[x][y];

      if (cell(actual).id == -1) {
        if (maxDist < distancia) {
          maxDist = distancia;
          dir_final = dir_original;
        }
      }
      
      if (maxDist <= 2) {
        for(auto i : pq) {
          k = i;
          if (k != dir) {
            Pos m = actual;
            m += Dir(k);
            if (pos_ok(m) and dist[m.i][m.j] == -1) {
              Cell c = cell(m); 
              if (c.type != Rock) {
                if (original) Q.push({m,k});
                else Q.push({m, dir_original});
                dist[m.i][m.j] = distancia + 1;
              } 
            }
          }
        }
      }
      else {
        VI permu = random_permutation(8);
        for (int i = 0; i < 8; ++i) {
          k = aux[permu[i]];
          Pos m = actual;
          m += Dir(k);
          if (pos_ok(m) and dist[m.i][m.j] == -1) {
              Cell c = cell(m); 
              if (c.type != Rock) {
                if (original) Q.push({m,k});
                else Q.push({m, dir_original});
                dist[m.i][m.j] = distancia + 1;
              } 
            }
        }
      }
      original = false;
    }
    return dir_final;
  }

  void pioneers_action() {
    VI my_p = pioneers(me());
    for (auto id : my_p) {
      Pos p = unit(id).pos;
      int dir;
      dir = p_direction(p, false);
      command(id, Dir(dir)); 
    }
  }

  void furyans_action() {
    VI my_f = furyans(me());
    for (auto id : my_f) {
      Pos p = unit(id).pos;
      int dir = p_direction(p, true); //Nos da la dirección a la cúal ir en busca de un Pioneer enemigo.
      command(id, Dir(dir));
    }
      //}
      
    //}
  }
  /**
   * Play method, invoked once per each round.
   */
  virtual void play () {
    sun_start = (round()%40)*2; //Where the sun starts. it gives us always 1 position further than ever needed. If ends at 79, it gives 0.
    sun_end = (sun_start + 40 - 1)%80; //Where the sun ends. Always gives 1 position less than ever needed. If ends at 42, it gives 41.
    pioneers_action(); 
    furyans_action();
  }

};


/**
 * Do not modify the following line.
 */
RegisterPlayer(PLAYER_NAME);