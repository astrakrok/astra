export const getJson = key => {
    const data = localStorage.getItem(key);
    return JSON.parse(data);
}

export const saveJson = (key, data) => {
    localStorage.setItem(key, JSON.stringify(data));
}

export const removeItem = key => {
    localStorage.removeItem(key);
}
