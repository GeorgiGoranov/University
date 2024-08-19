#include <stdio.h>
#include "ntk_5.4.h"
#include <windows.h> // For Sleep



typedef int TypeA; // Assuming the argument type is int for this example

unsigned __stdcall taskCode(void* arg) {
    task* t = (task*)arg;
    TypeA* a = (TypeA*)getArgument_task(t);
    DWORD startTime = GetTickCount(); // Get the start time

    while(!isTerminated_task(t) && (GetTickCount() - startTime) < 5000) {

        Sleep(100); // Just as an example to prevent a tight loop
    }

    // Cleanup or final actions here
    return 0; // Return value if needed
}

int main() {
    task* t1 = (task*)malloc(sizeof(task));
    TypeA par = 0; // Example parameter, adjust as necessary
    startNTK();

    create_task(t1, taskCode, &par, sizeof(TypeA), 0);

    Sleep(2000); // Wait for two seconds

    delete_task(t1);
    free(t1);

    return 0;
}
