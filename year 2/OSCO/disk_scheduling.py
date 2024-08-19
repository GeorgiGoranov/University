# Define the disk scheduling functions

def fifo(requests, start):
    total_time = 0
    current = start

    for request in requests:
        total_time += abs(request - current)
        current = request

    return total_time

def sstf(requests, start):
    total_time = 0
    current = start
    pending_requests = requests.copy()

    while pending_requests:
        closest_request = min(pending_requests, key=lambda r: abs(r - current))
        total_time += abs(closest_request - current)
        current = closest_request
        pending_requests.remove(closest_request)

    return total_time

def scan(requests, start):
    total_time = 0
    current = start
    direction = 1  # 1 for up, -1 for down
    pending_requests = sorted(requests)

    while pending_requests:
        if direction == 1:
            # Move upwards
            higher_requests = [r for r in pending_requests if r >= current]
            if higher_requests:
                next_request = min(higher_requests)
                total_time += abs(next_request - current)
                current = next_request
                pending_requests.remove(next_request)
            else:
                # Change direction
                direction = -1
        else:
            # Move downwards
            lower_requests = [r for r in pending_requests if r <= current]
            if lower_requests:
                next_request = max(lower_requests)
                total_time += abs(next_request - current)
                current = next_request
                pending_requests.remove(next_request)
            else:
                # Change direction
                direction = 1

    return total_time

def c_scan(requests, start):
    total_time = 0
    current = start
    pending_requests = sorted(requests)

    while pending_requests:
        # Move upwards
        higher_requests = [r for r in pending_requests if r >= current]
        if higher_requests:
            next_request = min(higher_requests)
            total_time += abs(next_request - current)
            current = next_request
            pending_requests.remove(next_request)
        else:
            # Move to the start
            total_time += (200 - current)
            current = 0

    return total_time

# Main function
def main():
    requests = [55, 58, 39, 18, 90, 160, 150, 38, 184]
    start = 100

    print("FIFO:")
    fifo_time = fifo(requests, start)
    print(f"Total time: {fifo_time} units")

    print("\nShortest Service Time First:")
    sstf_time = sstf(requests, start)
    print(f"Total time: {sstf_time} units")

    print("\nScan (Elevator):")
    scan_time = scan(requests, start)
    print(f"Total time: {scan_time} units")

    print("\nCircular Scan (C-SCAN):")
    c_scan_time = c_scan(requests, start)
    print(f"Total time: {c_scan_time} units")

if __name__ == "__main__":
    main()
