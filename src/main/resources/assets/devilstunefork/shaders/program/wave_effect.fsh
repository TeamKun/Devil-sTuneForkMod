#version 130

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform vec3 pos;
uniform float duration;
uniform sampler2D depthTex;

uniform float maxRadiuss[300];
uniform float radiuss[300];
uniform float centerX[300];
uniform float centerY[300];
uniform float centerZ[300];

in vec2 texCoord;

const float sharpness = 10;
const vec4 outerColor = vec4(0.8, 1.0, 0.9, 1.0);
const vec4 midColor = vec4(0.4, 0.5, 0.7, 1.0);
const vec4 innerColor = vec4(0.1, 0.4, 0.9, 1.0);
const vec4 scanlineColor = vec4(0.6, 1.0, 0.2, 1.0);

float scanlines() {
    return sin(gl_FragCoord.y)*0.5+0.5;
}

vec3 worldpos(float depth) {
    float z = depth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 worldSpacePosition = invViewMat * viewSpacePosition;

    return pos + worldSpacePosition.xyz;
}

void main() {
    float depth = texture2D(depthTex, texCoord).r;
    vec3 pos = worldpos(depth);

    float invs=1;

    for (int i = 0; i < 300; i++){
        float dist = distance(pos, vec3(centerX[i], centerY[i], centerZ[i]));
        if (dist < radiuss[i]  && dist > radiuss[i] - 1000 && depth < 1) {

            float ivc=radiuss[i]/maxRadiuss[i];
            if (invs>ivc)
            invs =  ivc;
        }
    }

    gl_FragColor = vec4(1-invs, 1-invs, 1-invs, 1);
}