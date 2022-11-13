import React from 'react';
import InMsg from './InMsg';
import OutMsg from './OutMsg';

function ChatBox(props) {
    const inMsg = "Text text text text text text text"
    const outMsg = "Text text text text text text text";

    return (
        <div className="bg-red w-4/5 h-screen scroll-smooth overflow-y-auto pb-8">
            <InMsg text={inMsg} />
            <OutMsg text={outMsg} />
            <InMsg text={inMsg} />
            <OutMsg text={outMsg} />
            <InMsg text={inMsg} />
            <OutMsg text={outMsg} />
            <InMsg text={inMsg} />
            <OutMsg text={outMsg} />
            <InMsg text={inMsg} />
            <OutMsg text={outMsg} />
            <InMsg text={inMsg} />
            <OutMsg text={outMsg} />
            <InMsg text={inMsg} />
            <OutMsg text={outMsg} />
        </div>
    )
}

export default ChatBox;
