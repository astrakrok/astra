export const addZeroToBegin = value => {
    return value*1 < 10 ? "0" + value : value;
}

export const getMinutesAndSeconds = totalSeconds => {
    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;

    return [minutes, seconds];
}

export const getCurrentUtcSeconds = () => {
    const currentUtcMilliseconds = new Date().getTime();
    return Math.round(currentUtcMilliseconds / 1000);
}

export const getHoursAndMinutes = totalMinutes => {
    const hours = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;

    return [hours, minutes];
}
