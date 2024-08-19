////task2 
//#include <stdio.h>
//#include <stdlib.h>
//#include <windows.h> // For Sleep and TerminateThread
//
//#define NUM_THREADS 5
//
//// Function prototype for the thread code
//unsigned __stdcall taskCode(void* arg);
//
//int main() {
//    // Create handles for threads
//    HANDLE hThreads[NUM_THREADS];
//    unsigned threadIDs[NUM_THREADS];
//
//    // Start the threads
//    for (int i = 0; i < NUM_THREADS; ++i) {
//        hThreads[i] = (HANDLE)_beginthreadex(NULL, 0, &taskCode, (void*)(intptr_t)(i + 1), 0, &threadIDs[i]);
//        if (hThreads[i] == NULL) {
//            printf("Thread creation failed\n");
//            return 1;
//        }
//        // Sleep for a short duration to stagger the thread starts
//        Sleep(100);
//    }
//
//    // Wait for a while to let the threads do their work
//    Sleep(5000); // Let them run for 5 seconds
//
//    // Close the threads in reverse order
//    for (int i = NUM_THREADS - 1; i >= 0; --i) {
//        // Terminate the thread
//        if (!TerminateThread(hThreads[i], 0)) {
//            printf("Thread termination failed\n");
//            return 1;
//        }
//        // Close the thread handle
//        CloseHandle(hThreads[i]);
//    }
//
//    return 0;
//}
//
//
//unsigned __stdcall taskCode(void* arg) {
//    int threadNumber = (int)(intptr_t)arg;
//    // Loop indefinitely until terminated
//    while (1) {
//        // Print some message indicating the thread is alive
//        printf("Thread %d is running...\n", threadNumber);
//        // Sleep for a while to simulate work
//        Sleep(1000);
//    }
//
//    return 0;
//}
//
////
//////task 3 
//
//#include <stdio.h>
//#include <stdlib.h>
//#include <windows.h> // For Sleep and TerminateThread
//
//#define NUM_THREADS 1000
//#define THREAD_DURATION_MS 500 // 0.5 seconds
//
//// Function prototype for the thread code
//unsigned __stdcall taskCode(void* arg);
//
//int main() {
//    // Create handles for threads
//    HANDLE hThreads[NUM_THREADS];
//    unsigned threadIDs[NUM_THREADS];
//
//    // Start the threads
//    for (int i = 0; i < NUM_THREADS; ++i) {
//        // Print thread ID during creation
//        printf("Creating thread %d\n", i + 1);
//
//        hThreads[i] = (HANDLE)_beginthreadex(NULL, 0, &taskCode, (void*)(intptr_t)(i + 1), 0, &threadIDs[i]);
//        if (hThreads[i] == NULL) {
//            printf("Thread creation failed\n");
//            return 1;
//        }
//
//        // Sleep for a short duration to stagger the thread starts
//        Sleep(1);
//    }
//
//    // Wait for all threads to complete
//    WaitForMultipleObjects(NUM_THREADS, hThreads, TRUE, INFINITE);
//
//    // Close the threads
//    for (int i = 0; i < NUM_THREADS; ++i) {
//        // Print thread ID during termination
//        printf("Terminating thread %d\n", i + 1);
//
//        // Close the thread handle
//        CloseHandle(hThreads[i]);
//    }
//
//    return 0;
//}
//
//// Thread code
//unsigned __stdcall taskCode(void* arg) {
//    int threadNumber = (int)(intptr_t)arg;
//
//    // Print thread ID during execution
//    printf("Thread %d is running...\n", threadNumber);
//
//    // Sleep for the specified duration
//    Sleep(THREAD_DURATION_MS);
//
//    // Print thread ID during termination
//    printf("Thread %d terminated.\n", threadNumber);
//
//    // Exit the thread
//    _endthreadex(0);
//
//    return 0;
//}
//
////
//////task 4
//
//#include <stdio.h>
//#include <stdlib.h>
//#include <windows.h> // For Sleep and CreateThread
//
//int global = 0; // Global variable
//
//// Function prototypes for the thread codes
//DWORD WINAPI incrementThread(LPVOID lpParam);
//DWORD WINAPI decrementThread(LPVOID lpParam);
//
//int main() {
//    // Create handles for threads
//    HANDLE hIncrementThread, hDecrementThread;
//
//    // Start the increment thread
//    hIncrementThread = CreateThread(NULL, 0, incrementThread, NULL, 0, NULL);
//    if (hIncrementThread == NULL) {
//        printf("Error creating increment thread\n");
//        return 1;
//    }
//
//    // Start the decrement thread
//    hDecrementThread = CreateThread(NULL, 0, decrementThread, NULL, 0, NULL);
//    if (hDecrementThread == NULL) {
//        printf("Error creating decrement thread\n");
//        return 1;
//    }
//
//    // Wait for both threads to finish
//    WaitForSingleObject(hIncrementThread, INFINITE);
//    WaitForSingleObject(hDecrementThread, INFINITE);
//
//    // Close thread handles
//    CloseHandle(hIncrementThread);
//    CloseHandle(hDecrementThread);
//
//    return 0;
//}
//
//// Thread code for incrementing global variable
//DWORD WINAPI incrementThread(LPVOID lpParam) {
//    for (int i = 0; i < 1000000; ++i) {
//        global++;
//    }
//    printf("Increment thread finished. Global value: %d\n", global);
//    return 0;
//}
//
//// Thread code for decrementing global variable
//DWORD WINAPI decrementThread(LPVOID lpParam) {
//    for (int i = 0; i < 1000000; ++i) {
//        global--;
//    }
//    printf("Decrement thread finished. Global value: %d\n", global);
//    return 0;
//}


//task 5 
#include <stdio.h>
#include <stdlib.h>
#include <windows.h> // For Sleep and CreateThread

int global = 0; // Global variable

// Function prototypes for the thread codes
DWORD WINAPI incrementThread(LPVOID lpParam);
DWORD WINAPI decrementThread(LPVOID lpParam);

int main() {
    // Create handles for threads
    HANDLE hIncrementThread, hDecrementThread;

    // Start the increment thread
    hIncrementThread = CreateThread(NULL, 0, incrementThread, NULL, 0, NULL);
    if (hIncrementThread == NULL) {
        printf("Error creating increment thread\n");
        return 1;
    }

    // Start the decrement thread
    hDecrementThread = CreateThread(NULL, 0, decrementThread, NULL, 0, NULL);
    if (hDecrementThread == NULL) {
        printf("Error creating decrement thread\n");
        return 1;
    }

    // Set priority for the decrementing thread
    if (!SetThreadPriority(hDecrementThread, THREAD_PRIORITY_ABOVE_NORMAL)) {
        printf("Error setting priority for decrement thread\n");
        return 1;
    }

    // Wait for both threads to finish
    WaitForSingleObject(hIncrementThread, INFINITE);
    WaitForSingleObject(hDecrementThread, INFINITE);

    // Close thread handles
    CloseHandle(hIncrementThread);
    CloseHandle(hDecrementThread);

    return 0;
}

// Thread code for incrementing global variable
DWORD WINAPI incrementThread(LPVOID lpParam) {
    for (int i = 0; i < 1000000; ++i) {
        global++;
    }
    printf("Increment thread finished. Global value: %d\n", global);
    return 0;
}

// Thread code for decrementing global variable
DWORD WINAPI decrementThread(LPVOID lpParam) {
    for (int i = 0; i < 1000000; ++i) {
        global--;
    }
    printf("Decrement thread finished. Global value: %d\n", global);
    return 0;
}
