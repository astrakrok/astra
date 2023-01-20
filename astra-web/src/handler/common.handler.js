export const valueOrNull = value => {
    return value === "" ? null : value;
}

export const throttle = (fn, delay) => {
    let run = false;
    let result = null;
    let lastArgs = null;
    return (...args) => {
        lastArgs = args;
        if (!run) {
            run = true;
            result = fn(...lastArgs);
            lastArgs = null;
            setTimeout(() => {
                if (lastArgs != null) {
                    result = fn(...lastArgs);
                    lastArgs = null;
                }
                run = false;
            }, delay);
        }
        return result;
    }
}
