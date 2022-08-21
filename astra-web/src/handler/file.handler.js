export const getFirstOrNull = files => {
    if (!files || files.length < 1) {
        return null;
    }
    return files[0];
}

export const readContentOrNull = (
    file,
    onSuccess,
    onError
) => {
    if (file == null) {
        return null;
    }
    const reader = new FileReader();
    reader.readAsText(file);
    reader.onload = event => onSuccess(event.target.result);
    reader.onerror = () => onError("Error while reading a file")
}

export const hasExtension = (name, extension) => {
    if (name == null) {
        return false;
    }
    const fileExtension = name.split(".").pop();
    return fileExtension && fileExtension.toLowerCase() === extension;
}
