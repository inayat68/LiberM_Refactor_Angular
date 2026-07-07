@echo off
set NIB_PARANOID_LICENSE_KEY=rxg1bu9TBifBg9UpiL1CHNBwMrkQUkiCntvR7jzu91O/jFLtueTcaUV41rUNhY0V/GDD0PKvmrdqc3t346bRLETlH1GRE/v9N2jSSpEFafhwHRfCQHhwnGoSuHl9SAzxTN+JFyzEMTy4MVF2cD/ZH5nenPSlDc5eDn59cz9Y6PI/6m2Y1SICXEKfBXU7IbX0xs2Hm175wdS2HE5VcLxz8ut4wNBu2VY120Ru2BFn4iPvAqbsyaQ6dnXbBx0CNK0KaDYmow0u4Wl94+Ga2hzjOWtHo3msVo+rjzswKJQJ39SIUeNXZPWs1gL4rQ/Fcw99X0rOrP1dI5g9CRuQL+ybeqV3Tlq47pNMKdTvOp6z+1rKAsffdYcX8VBmY/sdVdcR9gXQvGgeuwNXiwuapFDwj/Rn+PUgsFNSo1WyR7nl3kSBQ1Oqd/flUh7pOMBZ3XvL
set CTOOL=nib-paranoid-1.7.15.exe
set DG_CONV_WORKSPACE_IN=%cd%
set ts=%DATE%_%TIME%
set ts=%ts:/=-%
set ts=%ts::=-%
set ts=%ts: =0%

%CTOOL% analyze %DG_CONV_WORKSPACE_IN%\logs %DG_CONV_WORKSPACE_IN%\AnalysisReport_%ts%.xlsx
