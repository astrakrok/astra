import {useEffect, useState} from "react";
import {getHoursAndMinutes, getMinutesAndSeconds} from "../handler/time.handler";

const useTimer = (seconds, callback) => {
    const [time, setTime] = useState(seconds);

    useEffect(() => {
        const interval = time > 0 ? setInterval(() => {
            setTime(previous => previous - 1);
        }, 1000) : callback();

        return () => clearInterval(interval);
    }, [time]);

    const getTimeValues = time => {
        const [totalMinutes, seconds] = getMinutesAndSeconds(time);
        const [hours, minutes] = getHoursAndMinutes(totalMinutes);
        
        return [hours, minutes, seconds];
    }

    return getTimeValues(time);
}

export default useTimer;
