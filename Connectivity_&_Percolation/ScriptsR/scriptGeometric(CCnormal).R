png(filename = "EdgesGeometricR01.png",
width = 1920, height = 1500,         
units = "px",                        
res = 300) 

data1 <- read.csv("n_10_q_1000_edge_random_r01.csv")
data2 <- read.csv("n_20_q_1000_edge_random_r01.csv")
data3 <- read.csv("n_50_q_1000_edge_random_r01.csv")
data4 <- read.csv("n_100_q_1000_edge_random_r01.csv")

# Asignar les columnes a las variables X i Y.
X <- data1$Q
Y1 <- data1$CC
Y2 <- data2$CC
Y3 <- data3$CC
Y4 <- data4$CC

#Calculem les diferencias entre els valors consecutius de cada vector per a cada valor diferent de n
diferenciasY1 <- diff(Y1)  # N = 10
diferenciasY2 <- diff(Y2)  # N = 20
diferenciasY3 <- diff(Y3)  # N = 50
diferenciasY4 <- diff(Y4)  # N = 100

# Agafem valors negatius ja que la diferencia es majoritariment negativa perque les CC van en ordre descendent
diferenciasY1 <- abs(diferenciasY1)
diferenciasY2 <- abs(diferenciasY2) 
diferenciasY3 <- abs(diferenciasY3) 
diferenciasY4 <- abs(diferenciasY4) 

#Màxima diferènncia a cada vector
max_diferenciaY1 <- as.numeric(max(diferenciasY1))  # N = 10
max_diferenciaY2 <- as.numeric(max(diferenciasY2))  # N = 20
max_diferenciaY3 <- as.numeric(max(diferenciasY3))  # N = 50
max_diferenciaY4 <- as.numeric(max(diferenciasY4))  # N = 100

#Caluclem l'índex (per tant la Q on succeeix la transició de fase) per cada n
indice_maxY1 <- which.max(diferenciasY1)  # N = 10
indice_maxY2 <- which.max(diferenciasY2)  # N = 20
indice_maxY3 <- which.max(diferenciasY3)  # N = 50
indice_maxY4 <- which.max(diferenciasY4)  # N = 100

#Gráfic lineal
plot(X, Y1, 
     type = "l",                    
     main = "Relació entre CC i la probabilitat q per a diferents N",  
     xlab = "Prob. q",                        
     ylab = "Nombre de  CC/Nºnodes",                       
     col = "blue",                          
     lwd = 2,
     ylim= c(min(Y4), max(Y4)))                              

#Disseny
lines(X, Y2, col = "red", lwd = 2)      
lines(X, Y3, col = "turquoise3", lwd = 2)    
lines(X, Y4, col = "brown", lwd = 2)   
grid(nx = NULL, ny = NULL, col = rgb(0.5, 0.5, 0.5, alpha = 0.2), lty = "dotted")

#Llegenda
legend("topright", legend = c("N = 10", "N = 20", 
                              "N = 50", "N = 100"), 
       col = c("blue", "red", "turquoise3", "brown"), 
       lwd = 2, 
       cex = 0.8)                           

dev.off()

