#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
// For sleep
#include <unistd.h>

// How many times a task accesses the disk
#define NUM_DISK_ACCESSES_T1 6
#define NUM_DISK_ACCESSES_T2 3
#define NUM_DISK_ACCESSES_T3 2

// Some cool library to help manage concurency when using shared resources between threads
sem_t disks_sem;
pthread_t t1, t2, t3; // Our threads
time_t start_time, end_time; // Using these we are going to change the values and when we do start_time - end_time we can get the time it took it to run


// Function for disc access it takes the number of the task/thread_id, how many times the task is ran, and how much times it takes to run the task
void disk_access(int task_num, int num_accesses, int access_time, int iat) {
    for (int i = 0; i < num_accesses; i++) {
        // This is a global variable so we can access it in the func
        // So the sem_wait decreases the value for the semaphore if it is greater that 0. If it is 0 that means all the resources are taken and it waits until there is resources for the task to run.
        sem_wait(&disks_sem);
        // Using the pthread_self we are able to get the actual id of the process running on the machine.
        printf("Task T: %lu accessing disk. Or task: %d\n", (unsigned long)pthread_self(), task_num);
        sleep(access_time); // Simulate disk access time
        // It frees upo the resources
        sem_post(&disks_sem);
        sleep(iat);
    }
}

// Function to simulate task T1
void *task_t1(void *arg) {
    int iat = *((int *) arg);
    disk_access(1, NUM_DISK_ACCESSES_T1, 1, iat);
    return NULL;
}

// Function to simulate task T2
void *task_t2(void *arg) {
    int iat = *((int *) arg);
    disk_access(2, NUM_DISK_ACCESSES_T2, 2, iat);
    return NULL;
}

// Function to simulate task T3
void *task_t3(void *arg) {
    int iat = *((int *) arg);
    disk_access(3, NUM_DISK_ACCESSES_T3, 3, iat);
    return NULL;
}


int main(){
    // The different test scenarios
    // Something amazing happens when I include the iat time drops by 2 seconds compared to if there is no sleep in the disc_access method
    int iat_regular = 2;
    int iat_burst = 0;
    // When you do a remainder of 5 you will always get something between 0-4
    int iat_random = (rand() % 5);

    // First arg is the pointer to the semaphore. Then with a 0 or another number we define whether the resources are shared. Then we defines how many of them we want in this case we want two since we have two disks
    sem_init(&disks_sem, 0, 3); // The maximum improvement would be with 3 threads. Since the code is made to use at most 3 threads. Further improvement could be to make the tasks themselves run on multiple threads.
    
    // Playing with priorities to see if I can catch them in htop-----------
    pthread_attr_t attr;
    pthread_attr_init(&attr);
    // pthread_attr_setschedpolicy(&attr, SCHED_FIFO);

    // Set thread priorities
    struct sched_param param;
    param.sched_priority = 10; // 0 is the highest priority don't use that
    pthread_attr_setschedparam(&attr, &param);

    pthread_setschedparam(pthread_self(), SCHED_FIFO, &param);

    // Regular experiment
    printf("Regular Experiment\n");
    // Using time start and end we are able to find the time everything required to run
    time(&start_time);
    // Only the first thread is going to have higher priority
    pthread_create(&t1, &attr, task_t1, &iat_regular);
    pthread_create(&t2, &attr, task_t2, &iat_regular);
    pthread_create(&t3, &attr, task_t3, &iat_regular);
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    pthread_join(t3, NULL);
    time(&end_time);
    printf("Time taken: %ld seconds\n\n", end_time - start_time);

    // Burst experiment
    printf("Burst Experiment\n");
    time(&start_time);
    pthread_create(&t1, &attr, task_t1, &iat_burst);
    pthread_create(&t2, &attr, task_t2, &iat_burst);
    pthread_create(&t3, &attr, task_t3, &iat_burst);
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    pthread_join(t3, NULL);
    time(&end_time);
    printf("Time taken: %ld seconds\n\n", end_time - start_time);

    // Random experiment
    printf("Random Experiment: %d\n", iat_random);
    time(&start_time);
    pthread_create(&t1, &attr, task_t1, &iat_random);
    pthread_create(&t2, &attr, task_t2, &iat_random);
    pthread_create(&t3, &attr, task_t3, &iat_random);
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);
    pthread_join(t3, NULL);
    time(&end_time);
    printf("Time taken: %ld seconds\n\n", end_time - start_time);

    // Destroy semaphore
    sem_destroy(&disks_sem);

    // Destroy attr
    pthread_attr_destroy(&attr);
}