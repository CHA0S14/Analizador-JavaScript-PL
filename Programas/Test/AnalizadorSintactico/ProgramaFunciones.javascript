/* Programa de ejemplo  */
/******* Jos� Luis Fuertes, 5, enero, 2018 *********/
/* El ejemplo incorpora elementos del lenguaje opcionales y elementos que no hay que implementar */

var chars s;	/* variable global cadena */

function int FactorialRecursivo (int n)	/* n: par�metro formal de la funci�n entera */
{
	if (n == 0)	return 1;
	return n * FactorialRecursivo (n - 1);	/* llamada recursiva */
}

function int FactorialDo (int n)
{
	var int factorial = 1;	// variable local inicializada a uno
	do
	{
		factorial *= n--;	// equivale a: factorial = factorial * n; n = n - 1;
	} while (n);		// mientras n no sea 0
	return factorial;	// devuelve el valor entero de la variable factorial
}

function int FactorialWhile ()
{
	var int factorial = 1, i;	// variables locales: factorial inicializada a 1 e i inicializada a 0 por omisi�n
	while (i < num)			// num es variable global entera sin declarar
	{
		factorial *= ++i;	// equivale a: i = i + 1; factorial = factorial * i;
	}
	return factorial;
}

function int FactorialFor (int n)
{
	var int i, factorial = 1;	/* variables locales */
	for (i = 1; i <= n; i++)
	{
		factorial *= i;
	}
	return factorial;
}

var int For, Do, While;	// tres variables globales

function imprime (chars s, chars msg, int f)	/* funci�n que recibe 3 argumentos */
{
	write (s); write (msg); write (f);
	write ("\n");	// imprime un salto de l�nea */
	return;	/* finaliza la ejecuci�n de la funci�n (en este caso, se podr�a omitir) */
}

function chars cadena (bool log)
{
	if (!log)
	{
		return s;
	}
	else
	{
		return "Fin";
	}
}	// fin cadena: funci�n que devuelve una cadena

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
			For = FactorialFor (num);
			While = FactorialWhile ();
			Do = FactorialDo (num);
			imprime (cadena (false), "recursivo es: ", FactorialRecursivo (num));
			imprime (s, "con do-while es: ", Do);
			imprime (s, "con while es: ", While);
			imprime (cadena (false), "con for es: ", For);
		}
}

function bool bisiesto (int a)	
{			
	return (a % 4 == 0 && a % 100 != 0 || a % 400 == 0);	//se tienen en cuenta la precedencia de operadores
} // fin de bisiesto: funci�n l�gica

function int dias (int m, int a)
{
	switch (m)
	{
		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
			return 31; break;
		case 4: case 6: case 9: case 11:
			return 30;
		case 2: return 1;
			break;
		default: return 0;
	}
} // fin de dias. Todos los return devuelven un entero y la funci�n es entera

function bool esFechaCorrecta (int d, int m, int a)	
{
	return m>=1 && m<=12 && d>=1 && d <= dias (m, a);
} //fin de esFechaCorrecta

function imprime2 (int v, int w)	
{
	write (v + w, "\n");
} //fin de imprime2

function potencia (int z, int dim)	
{
	var int s;	// Oculta a la global
	for (s=0; s < dim; s++)
	{
		z *= z;
		imprime ("Potencia:", " ", z);
	}
} // fin de potencia: funci�n que no devuelve nada

function demo ()	/* definici�n de la funci�n demo, sin argumentos y que no devuelve nada */
{
	var int i;	// Variables locales
	var int v0, v1, v2, v3, zv;
	var chars s;	// Oculta a la s global

	write ('Escriba "tres" n�meros: ');
	prompt (v1); prompt (v2); prompt (v3);

	if (!((v1 == v2) && (v1 != v3)))	/* NOT ((v1 igual a v2) AND (v1 distinto de v3))  */
	{
		write ('Escriba su nombre: ');
		prompt (s);
		v0 = v3;	/* si v2<v3, v0=v2; en otro caso v0=v3 */
		write (s);
	}
	s = "El primer valor era ";
	if (v1 != 0)
	{
		write (s, v1, ".\n");
	}
	else
	{
		write (s, 0, ".\n");	// imprime la cadena `El primer valor era 0.\n�
	}

	potencia (v0, 4);
	for (i=1; i <= 10; ++i)
	{
		zv+=i;
	}
	potencia (zv, 5);
	imprime2 (i, num);
	imprime ("", cadena(true), 666);
}

demo();
/* esto constituye la llamada a una funci�n sin argumentos. 
Es en este instante cuando se llama a esta funci�n y, por tanto, 
cuando se ejecuta todo el c�digo de dicha funci�n */