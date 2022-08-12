import {useEffect, useState} from "react";
import {getCurrentUtcSeconds, getHoursAndMinutes, getMinutesAndSeconds} from "../handler/time.handler";

const useTimer = (seconds, callback) => {
    const currentUtcSeconds = getCurrentUtcSeconds();
    const [time, setTime] = useState(seconds - currentUtcSeconds);

    useEffect(() => {
        const interval = time > 0 ? setInterval(() => {
            const currentUtcSeconds = getCurrentUtcSeconds();
            setTime(seconds - currentUtcSeconds);
        }, 1000) : callback();

        return () => {
            return clearInterval(interval);
        };
    }, [time]);

    const getTimeValues = time => {
        const [totalMinutes, seconds] = getMinutesAndSeconds(time);
        const [hours, minutes] = getHoursAndMinutes(totalMinutes);
        
        return [hours, minutes, seconds];
    }

    return getTimeValues(time);
}

export default useTimer;
