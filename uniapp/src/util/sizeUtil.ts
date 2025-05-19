export const sizeUnit = (() => {
    const KB = 1024,
        MB = 1024 * KB,
        GB = 1024 * MB,
        TB = 1024 * GB;

    return {TB, GB, MB, KB};
})();

export function formatSize(size: number, unit: string = null) {
    if (unit != null)
        return {size: Number((size / sizeUnit[unit]).toFixed(2)), unit};
    for (let current in sizeUnit) {
        if (size > sizeUnit[current])
            return {size: Number((size / sizeUnit[current]).toFixed(2)), current};
    }
    return {size: size, unit: "B"};
}