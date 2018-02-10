#include <stdio.h>
#include <stdlib.h>

void quantity (int number)
{
    int *series = malloc(sizeof(int) * number);
    int lenght = 1;
    int flag = 0;

    series[0] = 2; 
    
    for(int x = 3; x < number; x++)
    {
        flag = 0;

        for (int i = 0; i < lenght; i++ )
        {
            if (x % series[i] == 0)
            {
                flag = 1;
                break;
            }
        }

        if(flag == 0)
        {
            series [lenght] = x;
            lenght++; 
        }

    }

    for (int i = 0; i < lenght; i++)
    {
        printf("%d; ", series[i]);
    }

    free(series);
}

int main()
{
    int number = 0;

    printf("Ведите число\n");
    scanf("%d", &number);
    printf("Простые меньше ведённого:\n");

    if (number >= 2)
    {
        quantity ( number);
    }

    return 0;
}