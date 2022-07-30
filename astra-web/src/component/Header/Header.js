import AuthControl from '../AuthControl/AuthControl';
import Logo from '../Logo/Logo';
import SiteNavigation from "./SiteNavigation/SiteNavigation";
import ProfileActions from "./ProfileActions/ProfileActions";
import AuthConsumer from "../../context/auth/AuthConsumer";
import Spacer from '../Spacer/Spacer';
import './Header.css';

const Header = () => {
    return (
        <header className="full-width Header">
            <div className="container">
                <div className="row content">
                    <div className="s-hflex-start">
                        <Logo responsive="true" />
                        <AuthConsumer>
                            {
                                ({userData}) => userData ? (
                                    <>
                                        <SiteNavigation />
                                        <Spacer className="hide-on-small-only" width={40} />
                                        <ProfileActions />
                                    </>
                                ) : (
                                    <>
                                        <div className="equal-flex" />
                                        <AuthControl />
                                    </>
                                )
                            }
                        </AuthConsumer>
                    </div>
                </div>
            </div>
        </header>
    );
}

export default Header;
