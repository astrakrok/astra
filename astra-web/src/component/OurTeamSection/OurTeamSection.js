import {avatars} from "../../data/mock/avatars";
import {Avatar} from "../Avatar/Avatar";
import TextHeader from "../TextHeader/TextHeader";

export const OurTeamSection = () => {
    return (
        <div className="container OurTeamSection">
            <div className="row">
                <TextHeader text="Наша команда" />
                <div className="avatar-list s-hflex-center wrap-flex">
                    {
                        avatars.map((avatar, index) => <Avatar key={index} url={avatar.url} />)
                    }
                </div>
            </div>
        </div>
    );
}
