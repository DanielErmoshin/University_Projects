# Descomentar en el momento de querer exportar la imagen. Leer README.
#png(filename = "ComparacióSquareTriangle1000.png", ##### Ir cambiando el nombre #####
#    width = 1920, height = 1500,         
#    units = "px",                        
#    res = 300) 

# Aqui pones los CSV que quieres leer.
# En este caso comparamos el N_1000 Square vs el N_1000 de Triangle.
data1 <- read.csv("n_1000_q_1000_vertexs.csv")
data2 <- read.csv("n_1000_q_1000_vertex_triangles.csv")
# data 3 <- read.csv("n_1000_q_1000_vertex_random.csv")

# Asignar les columnes a las variables X i Y
X <- data1$Valor
Y1 <- data1$Indice
Y2 <- data2$Indice
# Y3 <- data3$Indice

#Aplicamos calculo necesario para hacer CC/N.
Y1 <- Y1/1000000 #Esto del Square
Y2 <- Y2/(((1000)*(1001))/2) #Esto del Triangle

#Gráfic lineal
plot(X, Y1, 
     type = "l",                    
     main = "Relació entre CC/Nºnodes i la probabilitat q per als diferents models",  
     xlab = "Prob q",                        
     ylab = "Nombre de  CC/Nºnodes",                       
     col = "blue",                          
     lwd = 2,
     ylim = c(min(c(Y1, Y2)), max(c(Y1, Y2))))                    

#Disseny
lines(X, Y2, col = "red", lwd = 2)      

grid(nx = NULL, ny = NULL, col = rgb(0.5, 0.5, 0.5, alpha = 0.2), lty = "dotted")

#Llegenda
legend("topright", legend = c("Quadrat N = 1000", "Triangle N = 1000"), 
       col = c("blue", "red"), 
       lwd = 2, 
       cex = 0.8)                             

# Descomentar en el momento de querer exportar la imagen. Leer README.
#dev.off()

