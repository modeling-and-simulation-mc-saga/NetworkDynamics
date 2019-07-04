set terminal pdfcairo enhanced solid color size 29cm,21cm fontscale .8

set title "RandomWalk (10,0000 walkers)"
set label 1 "BA network ({/:Italic n}=1,000,{/:Italic m}=2)" at 200,5500
set output "RandomWalk.pdf"
set xlabel "degree"
set ylabel "number of walkers"
set xtic 50
set ytic 1000
plot "RandomWalk-txt" ps 2 pt 7 notitle
