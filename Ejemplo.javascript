
switch (num);
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
		case 2: return 29;
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
	for (s=0; s < dim; s++);
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
	for (i=1; i <= 10; ++i);
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
