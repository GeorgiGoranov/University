#include <stdio.h>
#include <stdlib.h>

struct Snode {
    int value;
    struct Snode* next;
};

typedef struct Snode node;

struct list {
    node* head;
    int count; // Added count to keep track of the number of elements
};

typedef struct list LL;

// Function prototypes
void constructLL(LL* list);
void destructLL(LL* list);
void printLL(LL* list);
int countLL(LL* list);
void addRearLL(LL* list, int number);
void addFrontLL(LL* list, int number);
void deleteLL(LL* list, int number);
void copyLL(LL* dest, LL* src);

// Initialize list
void constructLL(LL* list) {
    list->head = NULL;
    list->count = 0; // Initialize count to 0
}

// Cleanup dynamically allocated memory
void destructLL(LL* list) {
    node* current = list->head;
    while (current != NULL) {
        node* temp = current;
        current = current->next;
        free(temp);
    }
    list->head = NULL;
    list->count = 0; // Reset count
}

// Print list on console
void printLL(LL* list) {
    node* current = list->head;
    printf("List: ");
    while (current != NULL) {
        printf("%d ", current->value);
        current = current->next;
    }
    printf("\n");
}

// Number of elements in the list
int countLL(LL* list) {
    return list->count;
}

// Add element at the rear
void addRearLL(LL* list, int number) {
    node* newNode = malloc(sizeof(node));
    newNode->value = number;
    newNode->next = NULL;

    if (list->head == NULL) {
        list->head = newNode;
    }
    else {
        node* current = list->head;
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = newNode;
    }
    list->count++; // Increment count
}

// Add element at the front
void addFrontLL(LL* list, int number) {
    node* newNode = malloc(sizeof(node));
    newNode->value = number;
    newNode->next = list->head;
    list->head = newNode;
    list->count++; // Increment count
}

// Remove element
void deleteLL(LL* list, int number) {
    node* current = list->head;
    node* prev = NULL;

    while (current != NULL && current->value != number) {
        prev = current;
        current = current->next;
    }

    if (current == NULL) {
        printf("Element not found in the list.\n");
        return;
    }

    if (prev == NULL) {
        list->head = current->next;
    }
    else {
        prev->next = current->next;
    }

    free(current);
    list->count--; // Decrement count
}

// Copy from one list to another
void copyLL(LL* dest, LL* src) {
    // Clear destination list
    destructLL(dest);

    node* current = src->head;
    while (current != NULL) {
        addRearLL(dest, current->value);
        current = current->next;
    }
}

int main(void) {
    LL list1, list2;
    constructLL(&list1);
    constructLL(&list2);

    printLL(&list1);
    addFrontLL(&list1, 3);
    addFrontLL(&list1, 5);
    addFrontLL(&list1, 2);
    addFrontLL(&list1, 18);
    addFrontLL(&list1, 12);

    printLL(&list1);
    printf("number of elements: %d\n", countLL(&list1));
    addRearLL(&list1, 4);
    printLL(&list1);
    addRearLL(&list1, 10);

    printLL(&list1);
    copyLL(&list2, &list1);
    printf("Copy of list1 (list2):\n");

    printLL(&list2);
    destructLL(&list1);
    destructLL(&list2);

    system("pause");
    return 0;
}
