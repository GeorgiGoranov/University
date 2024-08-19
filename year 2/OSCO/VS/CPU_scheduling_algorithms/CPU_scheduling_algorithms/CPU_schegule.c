#include <stdio.h>
#include <stdlib.h>


#include <stdbool.h>

#include <limits.h>

#define NUM_PROCESSES 5
#define QUANTUM 2

//typedef struct {
//    char id;
//    int arrival_time;
//    int service_time;
//} Process;

//void FCFS(Process processes[], int n) {
//    int* wait_time = (int*)malloc(n * sizeof(int));
//    int* turn_around_time = (int*)malloc(n * sizeof(int));
//    int total_wait_time = 0, total_turn_around_time = 0;
//
//    if (wait_time == NULL || turn_around_time == NULL) {
//        printf("Memory allocation failed\n");
//        return;
//    }
//
//    wait_time[0] = 0;
//    turn_around_time[0] = processes[0].service_time;
//
//    for (int i = 1; i < n; i++) {
//        wait_time[i] = turn_around_time[i - 1] - processes[i].arrival_time;
//        if (wait_time[i] < 0) {
//            wait_time[i] = 0; // Ensure no negative wait time
//        }
//        turn_around_time[i] = wait_time[i] + processes[i].service_time;
//    }
//
//    for (int i = 0; i < n; i++) {
//        total_wait_time += wait_time[i];
//        total_turn_around_time += turn_around_time[i];
//        printf("Process %c: Waiting Time = %d, Turnaround Time = %d\n", processes[i].id, wait_time[i], turn_around_time[i]);
//    }
//
//    printf("Average Waiting Time: %.2f\n", (float)total_wait_time / n);
//    printf("Average Turnaround Time: %.2f\n", (float)total_turn_around_time / n);
//
//    free(wait_time);
//    free(turn_around_time);
//}
//
//int main() {
//    Process processes[NUM_PROCESSES] = {
//        {'A', 0, 3},
//        {'B', 2, 6},
//        {'C', 4, 4},
//        {'D', 6, 5},
//        {'E', 8, 2}
//    };
//
//    FCFS(processes, NUM_PROCESSES);
//
//    return 0;
//}



//void RoundRobin(Process processes[], int n, int quantum) {
//    int* remaining_time = (int
//        *)malloc(n * sizeof(int));
//    int* wait_time = (int*)malloc(n * sizeof(int));
//    int* turn_around_time = (int*)malloc(n * sizeof(int));
//    int total_wait_time = 0, total_turn_around_time = 0;
//    int time = 0;
//    bool done;
//
//    if (remaining_time == NULL || wait_time == NULL || turn_around_time == NULL) {
//        printf("Memory allocation failed\n");
//        return;
//    }
//
//    for (int i = 0; i < n; i++) {
//        remaining_time[i] = processes[i].service_time;
//    }
//
//    do {
//        done = true;
//        for (int i = 0; i < n; i++) {
//            if (remaining_time[i] > 0) {
//                done = false;
//                if (remaining_time[i] > quantum) {
//                    time += quantum;
//                    remaining_time[i] -= quantum;
//                }
//                else {
//                    time += remaining_time[i];
//                    wait_time[i] = time - processes[i].service_time;
//                    remaining_time[i] = 0;
//                }
//            }
//        }
//    } while (!done);
//
//    for (int i = 0; i < n; i++) {
//        turn_around_time[i] = processes[i].service_time + wait_time[i];
//        total_wait_time += wait_time[i];
//        total_turn_around_time += turn_around_time[i];
//        printf("Process %c: Waiting Time = %d, Turnaround Time = %d\n", processes[i].id, wait_time[i], turn_around_time[i]);
//    }
//
//    printf("Average Waiting Time: %.2f\n", (float)total_wait_time / n);
//    printf("Average Turnaround Time: %.2f\n", (float)total_turn_around_time / n);
//
//    free(remaining_time);
//    free(wait_time);
//    free(turn_around_time);
//}
//
//int main() {
//    Process processes[NUM_PROCESSES] = {
//        {'A', 0, 3},
//        {'B', 2, 6},
//        {'C', 4, 4},
//        {'D', 6, 5},
//        {'E', 8, 2}
//    };
//
//    RoundRobin(processes, NUM_PROCESSES, QUANTUM);
//
//    return 0;
//}

//typedef struct {
//    char id;
//    int arrival_time;
//    int service_time;
//    bool completed;
//} Process;
//
//void SPN(Process processes[], int n) {
//    int* wait_time = (int*)malloc(n * sizeof(int));
//    int* turn_around_time = (int*)malloc(n * sizeof(int));
//    int total_wait_time = 0, total_turn_around_time = 0;
//    int completed = 0, current_time = 0;
//
//    if (wait_time == NULL || turn_around_time == NULL) {
//        printf("Memory allocation failed\n");
//        return;
//    }
//
//    for (int i = 0; i < n; i++) {
//        wait_time[i] = 0;
//        turn_around_time[i] = 0;
//        processes[i].completed = false;
//    }
//
//    while (completed < n) {
//        int min_service_time = INT_MAX;
//        int shortest_job_index = -1;
//
//        for (int i = 0; i < n; i++) {
//            if (!processes[i].completed && processes[i].arrival_time <= current_time && processes[i].service_time < min_service_time) {
//                min_service_time = processes[i].service_time;
//                shortest_job_index = i;
//            }
//        }
//
//        if (shortest_job_index == -1) {
//            current_time++;
//            continue;
//        }
//
//        Process* shortest_job = &processes[shortest_job_index];
//
//        current_time += shortest_job->service_time;
//        wait_time[shortest_job_index] = current_time - shortest_job->arrival_time - shortest_job->service_time;
//        if (wait_time[shortest_job_index] < 0) {
//            wait_time[shortest_job_index] = 0;
//        }
//        turn_around_time[shortest_job_index] = current_time - shortest_job->arrival_time;
//
//        shortest_job->completed = true;
//        completed++;
//    }
//
//    for (int i = 0; i < n; i++) {
//        total_wait_time += wait_time[i];
//        total_turn_around_time += turn_around_time[i];
//        printf("Process %c: Waiting Time = %d, Turnaround Time = %d\n", processes[i].id, wait_time[i], turn_around_time[i]);
//    }
//
//    printf("Average Waiting Time: %.2f\n", (float)total_wait_time / n);
//    printf("Average Turnaround Time: %.2f\n", (float)total_turn_around_time / n);
//
//    free(wait_time);
//    free(turn_around_time);
//}
//
//int main() {
//    Process processes[NUM_PROCESSES] = {
//        {'A', 0, 3},
//        {'B', 2, 6},
//        {'C', 4, 4},
//        {'D', 6, 5},
//        {'E', 8, 2}
//    };
//
//    SPN(processes, NUM_PROCESSES);
//
//    return 0;
//}


typedef struct {
    char id;
    int arrival_time;
    int service_time;
    int remaining_time;
} Process;

void SRT(Process processes[], int n) {
    int* wait_time = (int*)malloc(n * sizeof(int));
    int* turn_around_time = (int*)malloc(n * sizeof(int));
    int total_wait_time = 0, total_turn_around_time = 0;
    int completed = 0, current_time = 0, shortest_index = -1, min_remaining_time = INT_MAX;
    bool check = false;

    if (wait_time == NULL || turn_around_time == NULL) {
        printf("Memory allocation failed\n");
        return;
    }

    for (int i = 0; i < n; i++) {
        processes[i].remaining_time = processes[i].service_time;
        wait_time[i] = 0;
        turn_around_time[i] = 0;
    }

    while (completed != n) {
        for (int i = 0; i < n; i++) {
            if (processes[i].arrival_time <= current_time && processes[i].remaining_time > 0 && processes[i].remaining_time < min_remaining_time) {
                min_remaining_time = processes[i].remaining_time;
                shortest_index = i;
                check = true;
            }
        }

        if (!check) {
            current_time++;
            continue;
        }

        processes[shortest_index].remaining_time--;
        min_remaining_time = processes[shortest_index].remaining_time;
        if (min_remaining_time == 0) {
            min_remaining_time = INT_MAX;
        }

        if (processes[shortest_index].remaining_time == 0) {
            completed++;
            check = false;
            int finish_time = current_time + 1;
            wait_time[shortest_index] = finish_time - processes[shortest_index].service_time - processes[shortest_index].arrival_time;
            if (wait_time[shortest_index] < 0) {
                wait_time[shortest_index] = 0;
            }
            turn_around_time[shortest_index] = finish_time - processes[shortest_index].arrival_time;
        }
        current_time++;
    }

    for (int i = 0; i < n; i++) {
        total_wait_time += wait_time[i];
        total_turn_around_time += turn_around_time[i];
        printf("Process %c: Waiting Time = %d, Turnaround Time = %d\n", processes[i].id, wait_time[i], turn_around_time[i]);
    }

    printf("Average Waiting Time: %.2f\n", (float)total_wait_time / n);
    printf("Average Turnaround Time: %.2f\n", (float)total_turn_around_time / n);

    free(wait_time);
    free(turn_around_time);
}

int main() {
    Process processes[NUM_PROCESSES] = {
        {'A', 0, 3},
        {'B', 2, 6},
        {'C', 4, 4},
        {'D', 6, 5},
        {'E', 8, 2}
    };

    SRT(processes, NUM_PROCESSES);

    return 0;
}
