import React from 'react'
import ReactPlayer from "react-player";

function Video(){
    return (
    <div>
        <ReactPlayer playing controls={true} url="https://www.youtube.com/watch?v=EKflgn8Ah4s" />
    </div>
    )
}

export default Video