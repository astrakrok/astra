import {useState} from "react";
import "./ScrollTopButton.css";

const ScrollTopButton = () => {
    const [hidden, setHidden] = useState(true);

    const toggleHidden = () => {
        const scrolled = document.documentElement.scrollTop;
        if (scrolled > 300) {
            setHidden(false);
        } else {
            setHidden(true);
        }
    }

    const scrollTop = () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    }

    window.addEventListener("scroll", toggleHidden);

    return hidden ? null : (
        <div className="ScrollTopButton s-vflex" onClick={scrollTop}>
            <i className="material-icons medium">arrow_upward</i>
        </div>
    );
}

export default ScrollTopButton;
