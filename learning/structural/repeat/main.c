#include <stdio.h>
#include <stdlib.h>

int repetition(int mass[], int size, int *pointer)
{
    int max = 1;
    int telly = mass[0];
    int value = 1;

    for(int x=0; x < size; x++)
    {
        value=1;
        for(int i = x + 1; i < size; i++)
        {
            if(mass[x] == mass[i])
            {
                value++;
            }
        }

        if(value > max)
        {
            max = value;
            telly = mass[x];
        }
    }
    *pointer = telly;
    return max;

}

int main()
{
    int size = 0;
    int number = 0;
    int result = 1;

    printf("Введите рамер масива:\n");
    scanf("%d", &size);
    int* mass = malloc(sizeof(int) * size);

    printf("Введите элементы масива:\n");
    for(int i = 0; i < size; i++)
    {
        scanf("%d", &mass[i]);
    } 
    result = repetition(mass, size, &number);
    printf("Число повторений : %d \n", result);
    printf("Повторяемое значение : %d \n", number);
    return 0;
}