import numpy as np

# Define the states (rooms)
states = [0, 1, 2, 3, 4, 5]

# Define the actions (movements between rooms)
actions = [0, 1, 2, 3, 4, 5]

# Rewards matrix
rewards = np.array([
    [-1, -1, -1, -1, 0, -1],
    [-1, -1, -1, 0, -1, 100],
    [-1, -1, -1, 0, -1, -1],
    [-1, 0, 0, -1, 0, -1],
    [0, -1, -1, 0, -1, 100],
    [-1, 0, -1, -1, 0, 100]
])

# Initialize with zeros
Q = np.zeros((len(states), len(actions)))

learning_rate = 0.8
discount_factor = 0.8
epsilon = 1.0  # exploration rate
epsilon_decay = 0.995  # exploration rate decay
min_epsilon = 0.1
n_episodes = 1000

for episode in range(n_episodes):
    # Start from a random state
    state = np.random.randint(0, len(states))

    while True:
        # epsilon-greedy strategy
        # exploration
        if np.random.rand() < epsilon:
            action = np.random.choice(actions)
        # exploitation
        else:
            action = np.argmax(Q[state])

        # validate action (not negative reward)
        while rewards[state, action] == -1:
            action = np.random.choice(actions)

        next_state = action
        reward = rewards[state, action]

        # Update the Q-matrix
        best_next_action = np.argmax(Q[next_state])
        Q[state, action] = Q[state, action] + learning_rate * (
                reward + discount_factor * Q[next_state, best_next_action] - Q[state, action])

        state = next_state

        # goal reached
        if state == 5:
            break

    # reduce exploration rate
    if epsilon > min_epsilon:
        epsilon *= epsilon_decay

np.set_printoptions(precision=2, suppress=True)

print("Trained Q-matrix:")
print(Q)
