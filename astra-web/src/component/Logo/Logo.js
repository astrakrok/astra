import {Link} from 'react-router-dom';
import {app} from '../../constant/app';
import {page} from '../../constant/page';
import './Logo.css';

const Logo = ({
                  responsive = false
              }) => {
    return (
        <Link to={page.home} className="Logo s-hflex-start">
            <div className="flower" style={{
                backgroundImage: `url("/images/logo.png")`
            }}/>
            <div className={`title s-vflex-center${responsive ? " hide-on-small-only" : ""}`}>{app.name}</div>
        </Link>
    );
}

export default Logo;
