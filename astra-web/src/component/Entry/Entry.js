import { InfoText } from '../InfoText/InfoText';
import './Entry.css';
import entry from './entry.png';

export const Entry = () => {
    return (
        <div className="container Entry">
            <div className="row m-hflex">
                <div className="col s12 m6">
                    <img src={entry} alt="entry" />
                </div>
                <div className="col s12 m6 s-vflex-center">
                    <InfoText>Lorem ipsum dolor sit amet, consectetur adipiscing elit ut aliquam</InfoText>
                </div>
            </div>
        </div>
    );
}
