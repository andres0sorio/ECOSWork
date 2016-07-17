#!/usr/bin/python

def replaceLatin( instring ):

    try:
        strflat = instring.decode('utf-8', 'replace').replace(unichr(241),'n').replace(unichr(209),'n')
        strflat = strflat.replace(unichr(225), 'a').replace(unichr(233),'e').replace(unichr(237),'i').replace(unichr(243),'o').replace(unichr(250),'u')
        strflat = strflat.replace(unichr(193), 'A').replace(unichr(201),'E').replace(unichr(205),'I').replace(unichr(211),'O').replace(unichr(218),'U')
        strflat = strflat.encode()
    except UnicodeEncodeError:
        return instring

    return strflat

