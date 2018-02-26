#include <stdio.h>
#include <string.h>

#define LENGHT_TEXT 100
#define LENGHT_KEY 20

int find(char text[], char key[])
{
    int flag = 1;
    int lenght = strlen(key);

    for(int i = 0; i < LENGHT_TEXT - lenght; i++)
    {
        flag = 1;
        for(int j = 0; j < lenght; j++)
        {
            if(text[i + j] != key[j])
            {
                flag = 0;
                break;
            }
        }

        if(flag == 1)
        {
            return i;
        }
    } 

    return -1;
}

int main()
{
    char text[LENGHT_TEXT] = {0};
    char key[LENGHT_KEY] = {0};
    int number = -1;

    printf("Введите строку, и подстроку\n");
    scanf("%s", &text);
    scanf("%s", &key);

    number = find(text, key);

    if(number >= 0)
    {
        number++;
        printf("Подсрока (%s) начинается с %d-го символа\n", key, number);
    }
    else
    {
        printf("Подсрока не найдена\n");
    }

    return 0;
}