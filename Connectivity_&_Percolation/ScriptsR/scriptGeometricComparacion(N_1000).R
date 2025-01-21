png(filename = "ComparacióRandomR01R0025.png",
    width = 1920, height = 1500,         
    units = "px",                        
    res = 300) 

data1 <- read.csv("n_100_q_1000_edge_random_r0025.csv")
data2 <- read.csv("n_100_q_1000_edge_random_r01.csv")

# Asignar les columnes a las variables X i Y
X <- data1$Q
Y1 <- data1$CC
Y2 <- data2$CC
# Y3 <- data3$Indice

#Aplicamos calculo necesario para hacer CC/N.
Y1 <- Y1/10000
Y2 <- Y2/10000

#Gráfic lineal
plot(X, Y1, 
     type = "l",                    
     main = "Relació entre CC/Nºnodes i la prob. q per als diferents models",  
     xlab = "Prob q",                        
     ylab = "Nombre de  CC/Nºnodes",                       
     col = "blue",                          
     lwd = 2,
     ylim = c(min(c(Y1, Y2)), max(c(Y1, Y2))))                    

#Disseny
lines(X, Y2, col = "red", lwd = 2)      

grid(nx = NULL, ny = NULL, col = rgb(0.5, 0.5, 0.5, alpha = 0.2), lty = "dotted")

#Llegenda
legend("topright", legend = c("r = 0.025", "r = 0.1"), 
       col = c("blue", "red"), 
       lwd = 2, 
       cex = 0.8)                             

dev.off()

