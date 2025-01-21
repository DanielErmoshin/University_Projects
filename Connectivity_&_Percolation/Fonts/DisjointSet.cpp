#include "DisjointSet.h"

using namespace std;


DisjointSet::DisjointSet(const map<int, set<int>> &AdjList, int n): dsuf(n) 
{
	int i = 0;
	for (map<int, set<int>>::const_iterator it = AdjList.begin(); it != AdjList.end(); ++it)
	{
		int x = it->first;
		for (; i < x; ++i)
		{
			dsuf[i].parent = -1;
			dsuf[i].rank = 0;
		}
		dsuf[x].rank = 0;
		dsuf[x].parent = x;
		++i;
	}
	for (; i < n; ++i) 
	{
		dsuf[i].parent = -1;
		dsuf[i].rank = 0;
	}
}


int DisjointSet::find(int v)
{
	if (v != dsuf[v].parent)
		dsuf[v].parent = find(dsuf[v].parent);
    return dsuf[v].parent;
}


void DisjointSet::link(int x, int y) 
{
	if (dsuf[x].rank > dsuf[y].rank)
		dsuf[y].parent = x;
	else 
	{
		dsuf[x].parent = y;
		if (dsuf[x].rank == dsuf[y].rank)
			++dsuf[y].rank;
	}
}


void DisjointSet::unionSets(int x,int y)
{
	link(find(x), find(y));
}


pair<int,int> DisjointSet::countComponents(int n) {
	vector<int> v(n,0);
	for (int i = 0; i < n; ++i)	//trobar arrel representant de cada "conjunt/component connexa" per a cada vertex del graf
	{
		if (dsuf[i].parent != -1)
			v[find(dsuf[i].parent)]++;
	}
	int max = 0;
	int imax = -1;
	int count = 0;
	for (int i = 0; i < n; ++i) //trobar nombre de components connexes, i la més gran
	{
		if (v[i])
		{
			++count;
			if (v[i] > max)
				max = v[i];
		}
	}
	return pair<int,int> (count,max);
}