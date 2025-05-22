fun main(){
println("Digite sua nota: ")
var nota=readLine()?.toIntOrNull()

if (nota==null){
println("errado!")
}
else if (nota<5){
println("abaixo da media")
}
else if (nota==5){
println("na media")
}
else{
println("acima da media")
}
}