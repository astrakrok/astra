import "./PhotoPreview.css";

const PhotoPreview = ({
    src,
    size = 150,
    shape = "circle"
}) => {
    const style = {
        backgroundImage: `url(${src})`,
        width: `${size}px`,
        height: `${size}px`
    };

    return (
        <div className={`PhotoPreview stretch-background ${shape}`} style={style} />
    );
}

export default PhotoPreview;
