import random
import os

#Prepare the common text file
file_path = "./t1.txt"

# Read records from the file
with open(file_path, "r") as f:
    records = f.readlines()

# Ensure records are stripped of newline characters
records = [record.strip() for record in records]

# Add unique keys
records_with_keys = [f"{i:04d}{record}" for i, record in enumerate(records)]

# Randomize record order for Pile file
random.shuffle(records_with_keys)

# Save the Pile file
with open("pile_file.txt", "w") as f:
    for record in records_with_keys:
        f.write(record + "\n")

# Construct the transaction file
keys = [record[:4] for record in records_with_keys]

# in case there are fewer than 20 records
num_samples = min(20, len(keys))
transaction_keys = random.sample(keys, num_samples)

with open("transaction_file.txt", "w") as f:
    for key in transaction_keys:
        f.write(key + "\n")

#Experiment 1 - Pile file
def search_pile_file(transaction_keys, pile_file):
    with open(pile_file, "r") as f:
        records = f.readlines()

    byte_accesses = 0
    for key in transaction_keys:
        for record in records:
            byte_accesses += len(record)
            if record.startswith(key):
                break

    average_access_time = byte_accesses / len(transaction_keys)
    return average_access_time

pile_avg_access_time = search_pile_file(transaction_keys, "pile_file.txt")
print(f"Pile File - Average Access Time: {pile_avg_access_time} bytes")

# Construct the Sequential file
sequential_records = sorted(records_with_keys)
sequential_file = []

for record in sequential_records:
    fixed_length_record = record[:80].ljust(80, ' ')
    sequential_file.append(fixed_length_record)

with open("sequential_file.txt", "w") as f:
    for record in sequential_file:
        f.write(record + "\n")

# Experiment 2 - Sequential file
def search_sequential_file(transaction_keys, sequential_file):
    with open(sequential_file, "r") as f:
        records = f.readlines()

    byte_accesses = 0
    for key in transaction_keys:
        for record in records:
            byte_accesses += len(record)
            if record.startswith(key):
                break

    average_access_time = byte_accesses / len(transaction_keys)
    return average_access_time

sequential_avg_access_time = search_sequential_file(transaction_keys, "sequential_file.txt")
print(f"Sequential File - Average Access Time: {sequential_avg_access_time} bytes")

# Compute storage efficiency
effective_file_size = sum(len(record.strip()) for record in sequential_file)
total_file_size = os.path.getsize("sequential_file.txt")
storage_efficiency = effective_file_size / total_file_size
print(f"Storage Efficiency: {storage_efficiency:.2f}")

