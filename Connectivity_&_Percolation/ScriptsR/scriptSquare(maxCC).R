# Descomentar en el momento de querer exportar la imagen. Leer README.
png(filename = "vertexsquaremaxCC1000.png", ##### Ir cambiando el nombre #####
    width = 1920, height = 1500,         
    units = "px",                        
res = 300) 

#Aqui pones el CSV que quieres leer (SIEMPRE EL DE N_1000)
data1 <- read.csv("n_1000_q_1000_vertexs_maxCC.csv")

# Asignar les columnes a las variables X i Y
X <- data1$Q
Y1 <- data1$MaxCC

#Gráfic lineal -> Te hace el plot lineal de solo el CSV n_1000
plot(X, Y1, 
     type = "l",                    
     main = "Relació entre la mida del clúster més gran i la prob. q, N = 1000",  
     xlab = "Prob. q",                        
     ylab = "Mida del Clúster més gran",                       
     col = "brown",                          
     lwd = 2)                              # Ancho de la línea

grid(nx = NULL, ny = NULL, col = rgb(0.5, 0.5, 0.5, alpha = 0.2), lty = "dotted")

#Llegenda
legend("topright", legend = c("N = 1000"), 
       col = c("brown"), 
       lwd = 2, 
       cex = 0.8)                             

# Descomentar en el momento de querer exportar la imagen. Leer README.
dev.off()

