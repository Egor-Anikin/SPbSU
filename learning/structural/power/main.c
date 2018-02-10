#include <stdio.h>

int main()
{
    int number = 0;
    int degree = 0;
    int result = 1;

    printf("Введите чило, и степень в которую его нужно возвести\n");
    scanf("%d", &number);
    scanf("%d", &degree);

    for (int i = 0; i < degree; i++)
    {
        result *= number;
    } 

    printf("Результат : %d \n", result);
    return 0;
}