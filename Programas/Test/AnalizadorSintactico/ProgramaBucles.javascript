/* Programa de ejemplo  */
/******* Jos� Luis Fuertes, 5, enero, 2018 *********/
/* El ejemplo incorpora elementos del lenguaje opcionales y elementos que no hay que implementar */

var chars s;	/* variable global cadena */


var int factorial = 1;	// variable local inicializada a uno
do
{
	factorial *= n--;	// equivale a: factorial = factorial * n; n = n - 1;
} while (n);		// mientras n no sea 0


var int factorial = 1, i;	// variables locales: factorial inicializada a 1 e i inicializada a 0 por omisi�n
while (i < num)			// num es variable global entera sin declarar
{
	factorial *= ++i;	// equivale a: i = i + 1; factorial = factorial * i;
}

var int i, factorial = 1;	/* variables locales */
for (i = 1; i <= n; i++)
{
	factorial *= i;
}

var int For, Do, While;	// tres variables globales

// Parte del programa principal:
s = "El factorial ";	// Primera sentencia que se ejecutar�a

write (s);
write ("\nIntroduce un 'n�mero'.");
prompt (num);	/* se lee un n�mero del teclado y se guarda en la variable global num */

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
/* esto constituye la llamada a una funci�n sin argumentos. 
Es en este instante cuando se llama a esta funci�n y, por tanto, 
cuando se ejecuta todo el c�digo de dicha funci�n */