import {app} from "../../constant/app";
import Logo from "../Logo/Logo";
import Ref from "../Ref/Ref";
import "./Footer.css";

const Footer = () => {
    return (
        <div className="container Footer">
            <div className="s-vflex m-hflex">
                <div className="equal-flex">
                    <Logo />
                    <p className="description">Наша мета - якісна, інноваційна та доступна медична освіта</p>
                    <p className="copyright">&copy; {app.name} {new Date().getFullYear()}</p>
                </div>
                <div className="s-vflex">
                    <Ref to={`mailto:${app.email}`}>{app.email}</Ref>
                </div>
            </div>
        </div>
    );
}

export default Footer;
