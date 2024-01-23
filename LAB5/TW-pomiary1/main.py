import numpy as np
import matplotlib.pyplot as plt

noOperations = ["10^4", "10^5", "5*10^5", "10^6", "5*10^6", "10^7"]

times_3locks = np.array([[72, 73, 46, 51, 69],
                         [444, 400, 375, 425, 422],
                         [1891, 1843, 1840, 1773, 1739],
                         [3468, 3367, 3283, 3413, 3313],
                         [16177, 16223, 16135, 16002, 15981],
                         [38651, 35304, 33671, 33868, 35264]])

cpu_times_3locks = np.array([[-78, -93, -15, -78, -46],
                             [-31, -31, 0, -15, 0],
                             [343, 234, 375, 343, 375],
                             [640, 859, 906, 687, 718],
                             [3218, 4015, 3421, 3312, 3171],
                             [8640, 8546, 7625, 8203, 8171]])

times_4cond = np.array([[58, 47, 72, 52, 46],
                        [1475, 1357, 1320, 1340, 1338],
                        [6576, 6579, 6430, 6384, 6588],
                        [13288, 12784, 13078, 12894, 12702],
                        [63436, 66268, 70651, 65756, 63667],
                        [134730, 132845, 129813, 131231, 132986]])

cpu_times_4cond = np.array([[-46, -31, -78, -78, -62],
                            [140, 46, 15, 171, 62],
                            [500, 593, 500, 609, 578],
                            [1171, 1000, 1187, 1234, 1125],
                            [5359, 5937, 6953, 6234, 5937],
                            [11750, 12578, 12890, 12187, 12328]])


# Uśrednianie wyników
avg_times_3locks = np.mean(times_3locks, axis=1) / 1000
avg_cpu_times_3locks = np.mean(cpu_times_3locks, axis=1) / 1000

avg_times_4cond = np.mean(times_4cond, axis=1) / 1000
avg_cpu_times_4cond = np.mean(cpu_times_4cond, axis=1) / 1000

# print(avg_cpu_times_3locks / avg_times_3locks)
# print(avg_cpu_times_4cond / avg_times_4cond)

print(avg_times_4cond / avg_times_3locks)
print(avg_cpu_times_4cond / avg_cpu_times_3locks)

fig, ax = plt.subplots()

bar_width = 0.35  # Szerokość słupków
bar_positions = np.arange(len(noOperations))

ax.bar(bar_positions, avg_times_4cond, width=bar_width, color='green', label='4 cond')
ax.bar(bar_positions + bar_width, avg_times_3locks, width=bar_width, color='red', label='3 locks')
ax.bar(bar_positions, avg_cpu_times_4cond, width=bar_width, color='blue', linestyle='--', label='4cond (CPU)', alpha=0.7)
ax.bar(bar_positions + bar_width, avg_cpu_times_3locks, width=bar_width, color='orange', linestyle='--', label='3locks (CPU)', alpha=0.7)

ax.set_xticks(bar_positions + bar_width / 2)
ax.set_xticklabels(noOperations)
ax.set_xlabel('Liczba operacji (konsumpcje + produkcje)')
ax.set_ylabel('Czas [s]')
ax.legend(loc='upper right')
plt.title('Porównanie czasów dla różnej ilości operacji')

plt.savefig('diagrams/Porównanie czasów dla różnej ilości operacji.png')

################

# Ucięcie dwóch pierwszych rzędów
times_3locks = times_3locks[:2, :]
cpu_times_3locks = cpu_times_3locks[:2, :]
times_4cond = times_4cond[:2, :]
cpu_times_4cond = cpu_times_4cond[:2, :]


# Uśrednianie wyników
avg_times_3locks = np.mean(times_3locks, axis=1)
avg_cpu_times_3locks = np.mean(cpu_times_3locks, axis=1)

avg_times_4cond = np.mean(times_4cond, axis=1)
avg_cpu_times_4cond = np.mean(cpu_times_4cond, axis=1)

fig, ax = plt.subplots()

bar_width = 0.35  # Szerokość słupków
bar_positions = np.arange(len(noOperations[:2]))

ax.bar(bar_positions, avg_times_4cond, width=bar_width, color='green', label='4 cond')
ax.bar(bar_positions + bar_width, avg_times_3locks, width=bar_width, color='red', label='3 locks')
ax.bar(bar_positions, avg_cpu_times_4cond, width=bar_width, color='blue', linestyle='--', label='4cond (CPU)', alpha=0.7)
ax.bar(bar_positions + bar_width, avg_cpu_times_3locks, width=bar_width, color='orange', linestyle='--', label='3locks (CPU)', alpha=0.7)

ax.set_xticks(bar_positions + bar_width / 2)
ax.set_xticklabels(noOperations[:2])
ax.set_xlabel('Liczba operacji (konsumpcje + produkcje)')
ax.set_ylabel('Czas [ms]')
ax.legend(loc='upper right')
plt.title('Porównanie czasów dla różnej ilości operacji (przybliżenie na małe wartości)')

plt.savefig('diagrams/Porównanie czasów dla różnej ilości operacji zoomed.png')

################

generators = ["Math.random()", "java.util.Random()"]

times_3locks = [[1891, 1843, 1840, 1773, 1739],
[2174, 1914, 1978, 2035, 1970]]

cpu_times_3locks = [[343, 234, 375, 343, 375],
[421, 328, 359, 421, 328]]

times_4cond = [[6576, 6579, 6430, 6384, 6588],
[6847, 6740, 6752, 6768, 6749]]

cpu_times_4cond = [[500, 593, 500, 609, 578],
[578, 703, 562, 375, 312]]

# Uśrednianie wyników
avg_times_3locks = np.mean(times_3locks, axis=1) / 1000
avg_cpu_times_3locks = np.mean(cpu_times_3locks, axis=1) / 1000

avg_times_4cond = np.mean(times_4cond, axis=1) / 1000
avg_cpu_times_4cond = np.mean(cpu_times_4cond, axis=1) / 1000

# print(avg_cpu_times_3locks / avg_times_3locks)
# print(avg_cpu_times_4cond / avg_times_4cond)

print(avg_times_4cond / avg_times_3locks)
print(avg_cpu_times_4cond / avg_cpu_times_3locks)

fig, ax = plt.subplots()

bar_width = 0.35  # Szerokość słupków
bar_positions = np.arange(len(generators))

ax.bar(bar_positions, avg_times_4cond, width=bar_width, color='green', label='4 cond')
ax.bar(bar_positions + bar_width, avg_times_3locks, width=bar_width, color='red', label='3 locks')
ax.bar(bar_positions, avg_cpu_times_4cond, width=bar_width, color='blue', linestyle='--', label='4cond (CPU)', alpha=0.7)
ax.bar(bar_positions + bar_width, avg_cpu_times_3locks, width=bar_width, color='orange', linestyle='--', label='3locks (CPU)', alpha=0.7)

ax.set_xticks(bar_positions + bar_width / 2)
ax.set_xticklabels(generators)
ax.set_xlabel('Generator liczb losowych')
ax.set_ylabel('Czas [s]')
ax.legend(loc='upper right')
plt.title('Porównanie czasów dla różnych generatorów liczb losowych')

plt.savefig('diagrams/Porównanie czasów dla różnych generatorów liczb losowych.png')

#############

bufor_size = [20, 200]

times_3locks = [[1891, 1843, 1840, 1773, 1739],
[199, 161, 156, 182, 171]]

cpu_times_3locks = [[343, 234, 375, 343, 375],
[-78, -31, -46, -62, -46]]

times_4cond = [[6576, 6579, 6430, 6384, 6588],
[1383, 1366, 1413, 1366, 1377]]

cpu_times_4cond = [[500, 593, 500, 609, 578],
[15, 0, 0, -31, 0]]

# Uśrednianie wyników
avg_times_3locks = np.mean(times_3locks, axis=1) / 1000
avg_cpu_times_3locks = np.mean(cpu_times_3locks, axis=1) / 1000

avg_times_4cond = np.mean(times_4cond, axis=1) / 1000
avg_cpu_times_4cond = np.mean(cpu_times_4cond, axis=1) / 1000

# print(avg_cpu_times_3locks / avg_times_3locks)
# print(avg_cpu_times_4cond / avg_times_4cond)

print(avg_times_4cond / avg_times_3locks)
print(avg_cpu_times_4cond / avg_cpu_times_3locks)

fig, ax = plt.subplots()

bar_width = 0.35  # Szerokość słupków
bar_positions = np.arange(len(bufor_size))

ax.bar(bar_positions, avg_times_4cond, width=bar_width, color='green', label='4 cond')
ax.bar(bar_positions + bar_width, avg_times_3locks, width=bar_width, color='red', label='3 locks')
ax.bar(bar_positions, avg_cpu_times_4cond, width=bar_width, color='blue', linestyle='--', label='4cond (CPU)', alpha=0.7)
ax.bar(bar_positions + bar_width, avg_cpu_times_3locks, width=bar_width, color='orange', linestyle='--', label='3locks (CPU)', alpha=0.7)

ax.set_xticks(bar_positions + bar_width / 2)
ax.set_xticklabels(bufor_size)
ax.set_xlabel('Wielkość bufora')
ax.set_ylabel('Czas [s]')
ax.legend(loc='upper right')
plt.title('Porównanie czasów dla różnych wielkości bufora')

plt.savefig('diagrams/Porównanie czasów dla różnych wielkości bufora.png')

############

threads = [2, 6, 10, 50, 100, 500, 1000]

times_3locks = [[1716, 1670, 1592, 1705, 1665],
[1761, 1807, 1659, 1730, 1699],
[1891, 1843, 1840, 1773, 1739],
[6869, 6753, 6702, 6673, 6732],
[6936, 6757, 6786, 6737, 7109],
[7475, 7377, 7306, 7368, 7438],
[7809, 8865, 7541, 7603, 7597]]

cpu_times_3locks = [[734, 828, 703, 812, 625],
[640, 578, 500, 593, 562],
[343, 234, 375, 343, 375],
[109, 125, 31, 406, 140],
[125, 15, 46, 359, -15],
[62, 46, -78, 187, 15],
[406, -93, 46, 390, 218]]

times_4cond = [[1685, 1584, 1601, 1650, 1636],
[5046, 4820, 4799, 4876, 4769],
[6576, 6579, 6430, 6384, 6588],
[6693, 6778, 6654, 6844, 6788],
[7194, 6779, 6813, 6979, 6755],
[7418, 7479, 7332, 7284, 7291],
[7681, 7878, 7548, 7526, 7706]]

cpu_times_4cond = [[750, 546, 609, 718, 703],
[812, 718, 625, 750, 734],
[500, 593, 500, 609, 578],
[281, 265, 78, 187, 328],
[31, 390, 31, -31, 468],
[46, 31, 156, 453, 78],
[390, -46, 31, 62, 156]]

# Uśrednianie wyników
avg_times_3locks = np.mean(times_3locks, axis=1) / 1000
avg_cpu_times_3locks = np.mean(cpu_times_3locks, axis=1) / 1000

avg_times_4cond = np.mean(times_4cond, axis=1) / 1000
avg_cpu_times_4cond = np.mean(cpu_times_4cond, axis=1) / 1000

# print(avg_cpu_times_3locks / avg_times_3locks)
# print(avg_cpu_times_4cond / avg_times_4cond)

print(avg_times_4cond / avg_times_3locks)
print(avg_cpu_times_4cond / avg_cpu_times_3locks)

fig, ax = plt.subplots()

bar_width = 0.35  # Szerokość słupków
bar_positions = np.arange(len(threads))

ax.bar(bar_positions, avg_times_4cond, width=bar_width, color='green', label='4 cond')
ax.bar(bar_positions + bar_width, avg_times_3locks, width=bar_width, color='red', label='3 locks')
ax.bar(bar_positions, avg_cpu_times_4cond, width=bar_width, color='blue', linestyle='--', label='4cond (CPU)', alpha=0.7)
ax.bar(bar_positions + bar_width, avg_cpu_times_3locks, width=bar_width, color='orange', linestyle='--', label='3locks (CPU)', alpha=0.7)

ax.set_xticks(bar_positions + bar_width / 2)
ax.set_xticklabels(threads)
ax.set_xlabel('Ilość wątków')
ax.set_ylabel('Czas [s]')
ax.legend(loc='upper right')
plt.title('Porównanie czasów dla różnych ilości wątków')

plt.savefig('diagrams/Porównanie czasów dla różnych ilości wątków.png')
