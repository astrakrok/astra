import useTimer from "../../hook/useTimer";
import {addZeroToBegin} from "../../handler/time.handler";
import "./Timer.css";

const Timer = ({duration, onExpire}) => {
    const [hours, minutes, seconds] = useTimer(duration, onExpire);

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
