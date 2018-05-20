/* Programa de ejemplo  */
/******* José Luis Fuertes, 5, enero, 2018 *********/
/* El ejemplo incorpora elementos del lenguaje opcionales y elementos que no hay que implementar */

var chars s;	/* variable global cadena */

var int For, Do, While;	// tres variables globales

// Parte del programa principal:
s = "El factorial ";	// Primera sentencia que se ejecutaría

write (s);
write ("\nIntroduce un 'número'.");
prompt (num);	/* se lee un número del teclado y se guarda en la variable global num */

switch (num)
{
	case 1:
	case 0: write ("El factorial de ", num, " siempre es 1.\n"); break;
	default:
		if (num < 0)
		{
			write ('No existe el factorial de un negativo.\n');
		}
		else
		{
			s = "otro texto";
		}
}
/* esto constituye la llamada a una función sin argumentos. 
Es en este instante cuando se llama a esta función y, por tanto, 
cuando se ejecuta todo el código de dicha función */
