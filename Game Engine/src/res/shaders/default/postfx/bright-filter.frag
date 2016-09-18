#version 150 core
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors;

const vec3 weights = vec3(0.2126, 0.7152, 0.0722);

void main() {
	outColor = texture(colors, texCoord);
	float brightness = dot(outColor.rgb, weights);
	outColor = vec4(outColor.rgb * brightness, outColor.a);
}
