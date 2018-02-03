#include <stdio.h>

int main ()
{
    int number=0, degree=0, result=1;
    printf("Ведите чило, и степень в которую его нужно возвести");
    scanf("%d\n",&number);
    scanf("%d\n",&degree);
    for (int i=0; i<degree;i++)
        result *= number ;
    printf("Результат : %d \n",result);
    return 0;
}