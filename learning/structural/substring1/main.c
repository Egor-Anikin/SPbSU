#include <stdio.h>

#define lenght_text 100
#define lenght_key 20

int main()
{
    char text[lenght_text];
    char key[lenght_key];
    int number = -1;
    int flag = 1;
   

    printf("Введите строку, и подстроку\n");
    scanf("%s", &text);
    scanf("%s", &key);

    for(int i = 0; i < lenght_text - lenght_key; i++)
    {
        flag = 1;

        for(int j = 0; i < lenght_key; j++)
        {
            if(text[i+j] != key[j]);
            {
                flag = 0;
                break;
            }

        }

        if(flag = 1)
        {
            number = i;
        }
    } 

    if(number >= 0)
    {
        number++;
        printf("Подсрока (%s) начинается с %d-го символа\n",key, number);
    }
    else
    {
        printf("Подсрока не найдена\n");
    }
    return 0;
}