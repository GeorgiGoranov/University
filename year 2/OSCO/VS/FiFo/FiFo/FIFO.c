#include <stdio.h>

#define F 3
int frames[F];       // Memory frames
int lastFIFOindex = 0;  // Index to track the oldest page

// Function to check if a page is in memory
int chkPmem(int page_in) {
    for (int i = 0; i < F; i++) {
        if (frames[i] == page_in) {
            return 1;
        }
    }
    return 0;
}

// FIFO policy implementation
int FIFO() {
    int evict_index = lastFIFOindex;
    lastFIFOindex = (lastFIFOindex + 1) % F;
    return evict_index;
}

// Handle page fault
void handlePfault(int page_in) {
    int frame_index = FIFO();  // Get the frame index to replace
    frames[frame_index] = page_in;  // Replace the page in the frame
}

// Main function to simulate address retrieval
int getAddr(int page_in) {
    if (chkPmem(page_in)) {
        return 0;  // Page found in memory, no fault
    }
    handlePfault(page_in);
    return 1;  // Page fault occurred
}

int main() {
    int page_requests[] = { 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1 };
    int num_requests = sizeof(page_requests) / sizeof(page_requests[0]);
    int faults = 0;

    // Initialize frames to -1 indicating they are empty
    for (int i = 0; i < F; i++) {
        frames[i] = -1;
    }

    // Process each page request
    for (int i = 0; i < num_requests; i++) {
        faults += getAddr(page_requests[i]);
    }

    printf("Final frame content: ");
    for (int i = 0; i < F; i++) {
        printf("%d ", frames[i]);
    }
    printf("\nTotal page faults: %d\n", faults);

    return 0;
}
