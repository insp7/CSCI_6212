# For CS 6212 Project 3 Pseudo Polynomial Partition
import random
import time
def pseudo_partition(arr, n):
    total = sum(arr)
    print("Total is ", total)
    subset1 = []
    # If total is odd then no partition
    if total % 2 != 0:
        return [], [], False

    # initialize table 
    table = []
    for j in range(total // 2 + 1):
        row = []
        for i in range(n + 1):
            row.append(True)
        table.append(row)

    # initialize top row as true
    for i in range(n + 1):
        table[0][i] = True

    # initialize leftmost column,
    # except part[0][0], as False
    for i in range(1, total // 2 + 1):
        table[i][0] = False

    # fill the partition table in
    # bottom up manner
    for row in range(1, total // 2 + 1):
        for col in range(1, n + 1):
            table[row][col] = table[row][col - 1]
            # check if you can include current element or exclude it
            if row >= arr[col - 1]:
                table[row][col] = (table[row][col] or
                              table[row - arr[col - 1]][col - 1])
    if not table[total // 2][n]:
        return [],[], False

    subset1 = []
    subset2 = []
    row, col = total // 2, len(arr)

    while row >= 0 and col > 0:
        # check if current element is included in the subset or not the decrement current row based on the current element
        if table[row][col] and not table[row][col - 1]:
            subset1.append(arr[col - 1])
            row -= arr[col - 1]
        else:
            subset2.append(arr[col - 1])
        col -= 1

    return subset1, subset2, table[total // 2][n]

if __name__ == "__main__":
    n = [10,100, 300, 500, 1000, 5000, 8000, 10000]
    arr = []
    for val in n:
        row = []
        for _ in range(val):
            even_number = random.randint(1, 50) * 2  # Generate even numbers between 2 and 100
            row.append(even_number)
        arr.append(row)
    time_taken = []
    for val in arr:
        start_time = time.time()
        # Function call
        subset1, subset2, is_partition = pseudo_partition(val, len(val))
        if is_partition == True:
            print("Can be divided into two",
                "subsets of equal sum")
            # print(f"Subset 1: {subset1}\nSubset 2: {subset2}\n subsets sum: {sum(subset1)} and {sum(subset2)}")
        else:
            print("Can not be divided into ",
                "two subsets of equal sum")
        end_time = time.time()
        time_taken.append(end_time - start_time)
    print(time_taken)
