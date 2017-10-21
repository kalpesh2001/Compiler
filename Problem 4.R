
generateData <- function(no_points)  {
  
# Let's find average hypothesis for g(x) = a*x+b
  variance <- c()
  bias <- c()
  plot(-1:1,-1:1,type = "n")
  # Generate hypothesis using a pair of points
  point1 <- runif(no_points,-1.0,1.0)
  point1 <- cbind(point1,sinpi(point1))
  point2 <- runif(no_points,-1.0,1.0)
  point2 <- cbind(point2,sinpi(point2))
  slope <- (point2[,2] - point1[,2])/(point2[,1] - point1[,1])
  intercept <- point2[,2] - (slope*point2[,1])
  
  for (i in 1:length(slope))
  abline(intercept[i],slope[i],col = "gray")
  # Slope and intercept of average hypothesis (g bar)
  a_bar <- mean(slope)
  b_bar <- mean(intercept)
  abline(b_bar,a_bar, col = "red")
  
  # Generate fresh test points using uniform distribution. Calculate bias and variance
  test_x <- runif(no_points,-1.0,1.0)
  for (kk in 1:length(slope)) {
    variance <- c(variance,(slope[kk]*test_x + intercept[kk] - test_x*a_bar - b_bar))
    bias <- c(bias,(sinpi(test_x) - test_x*a_bar - b_bar))
  }
  bias_test <- mean(bias^2)
  variance_test <- mean(variance^2)
  cat("Bias and variance on test data:",bias_test,variance_test,"\n")
  #Calcuate bias and variance on training data
  point_combined = rbind(point1,point2)
  bias_train <- mean((a_bar*point_combined[,1]+b_bar - point_combined[,2])^2)
  variance_train <- mean((a_bar*point_combined[,1]+b_bar - point_combined[,2])^2)
  cat("Bias and variance on training data:",bias_train,variance_train,"\n")
  
  return(list(bias_test,variance_test))
}

driver <- function(no_runs) {
  bias <- c()
  variance <- c()
  for (i in 1:no_runs) {
    ret <- generateData(100)
    bias <- c(bias,ret[[1]])
    variance <- c(variance,ret[[2]])
  }
  cat("Average bias:", mean(bias),"Average variance:", mean(variance))
}
