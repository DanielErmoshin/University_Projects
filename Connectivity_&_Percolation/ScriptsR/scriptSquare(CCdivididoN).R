# Descomentar en el momento de querer exportar la imagen. Leer README.
png(filename = "vertexsquareCCperN.png",  ##### Ir cambiando el nombre #####
width = 1920, height = 1500,          
units = "px",                        
res = 300) 

#Aqui pones los CSV que quieres leer
data1 <- read.csv("n_50_q_1000_vertexs.csv")
data2 <- read.csv("n_100_q_1000_vertexs.csv")
data3 <- read.csv("n_250_q_1000_vertexs.csv")
data4 <- read.csv("n_500_q_1000_vertexs.csv")
data5 <- read.csv("n_1000_q_1000_vertexs.csv")

# Asignar les columnes a las variables X i Y
X <- data1$Valor
Y1 <- data1$Indice
Y2 <- data2$Indice
Y3 <- data3$Indice
Y4 <- data4$Indice
Y5 <- data5$Indice

# Al ser este el Script del gráfico donde dividimos CC/N, debemos de hacer este cálculo. 
# En este caso está puesto el cálculo para los Triangle Graphs.
Y1 <- Y1/(50*50)
Y2 <- Y2/(100*100)
Y3 <- Y3/(250*250)
Y4 <- Y4/(500*500)
Y5 <- Y5/(1000*1000)

#Gráfic lineal
plot(X, Y1, 
     type = "l",                    
     main = "Relació entre CC/Nºnodes i la probabilitat q per a diferents N",  
     xlab = "Prob. q",                        
     ylab = "Nombre de  CC/Nºnodes",                       
     col = "blue",                          
     lwd = 2)                              # Ancho de la línea

#Disseny
lines(X, Y2, col = "red", lwd = 2)      
lines(X, Y3, col = "turquoise3", lwd = 2)    
lines(X, Y4, col = "orange", lwd = 2)   
lines(X, Y5, col = "brown", lwd = 2)     

grid(nx = NULL, ny = NULL, col = rgb(0.5, 0.5, 0.5, alpha = 0.2), lty = "dotted")

#Llegenda
legend("topright", legend = c("N = 50", "N = 100", 
                              "N = 250", "N = 500", "N = 1000"), 
       col = c("blue", "red", "turquoise3", "orange", "brown"), 
       lwd = 2, 
       cex = 0.8)  
                                  
# Descomentar en el momento de querer exportar la imagen. Leer README.
dev.off()

