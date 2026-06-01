export class FileInfo {
    name: string
    isDirectory: boolean
    size: number

    constructor(name: string, isDirectory: boolean, size: number) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.size = size;
    }
}