#include <stdio.h>



long long store1less = 1; //used to be int
long long store2less = 2;
long long curAnswer = 0;

int main() {

        for (int i = 0; i <= 50; i++) {

                if (i == 0) {
                     printf("%d\n", store2less);
                }

                if (i == 2) {

                       curAnswer = store1less + store2less;

                }

                if (i > 2) {
                             formula();
                }


                if (i % 5 == 0 && i != 0) {
                        printf("%ld\n", curAnswer);
                }
        }
}

void formula() {
     store2less = store1less;
     store1less = curAnswer;
     curAnswer = store2less + store1less;

}


