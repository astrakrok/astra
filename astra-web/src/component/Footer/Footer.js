import {app} from "../../constant/app";
import {socialLinks} from "../../data/mock/social.links";
import Logo from "../Logo/Logo";
import Ref from "../Ref/Ref";
import SocialLink from "../SocialLink/SocialLink";
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
                    <div className="social-links s-hflex">
                        {
                            socialLinks.map((social, index) => <SocialLink key={index} social={social} />)
                        }
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Footer;
