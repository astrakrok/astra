import useTimer from "../../hook/useTimer";
import {addZeroToBegin} from "../../handler/time.handler";
import "./Timer.css";

// finishedAt must be UTC timestamp
const Timer = ({finishedAt, onExpire}) => {
    const [hours, minutes, seconds] = useTimer(finishedAt, onExpire);

    return (
        <div className="Timer s-hflex">
            <div className="hours">
                {addZeroToBegin(hours)}
            </div>
            <div className="colon">
                :
            </div>
            <div className="minutes">
                {addZeroToBegin(minutes)}
            </div>
            <div className="colon">
                :
            </div>
            <div className="seconds">
                {addZeroToBegin(seconds)}
            </div>
        </div>
    );
}

export default Timer;
