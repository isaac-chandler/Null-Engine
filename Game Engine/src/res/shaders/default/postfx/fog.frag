#version 150
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors;
uniform sampler2D depth;

uniform vec3 skyColor;
uniform float density;
uniform float cutoff;

void main() {
	float dist = texture(depth, texCoord).r;
	outColor = mix(texture(colors, texCoord), vec4(skyColor, 1), pow(dist, 1 / density) * step(cutoff, dist));
}
